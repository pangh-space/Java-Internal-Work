package com.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description DuduApplicationContext
 * @Author pangh
 * @Date 2022年11月20日
 * @Version v1.0.0
 */
public class DuduApplicationContext {

    private Class configClass;

    // BeanDefinition定义容器
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    // 定义Bean 单例池
    private Map<String,Object> singletonObject = new ConcurrentHashMap<>();
    // BeanPostProcess容器
    private List<BeanPostProcessor> beanPostProcessorList = new CopyOnWriteArrayList<>();

    public DuduApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描携带@Component注解的类
        scan(configClass);

        // 遍历BeanDefinitionMap，创建Bean
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            BeanDefinition beanDefinition = beanDefinitionEntry.getValue();
            if (beanDefinition.getScope().equals("singleton")) {
                //创建Bean
                Object singletonBean = createBean(beanName, beanDefinition);
                //放入Bean单例池
                singletonObject.put(beanName,singletonBean);
            }
        }
    }

    /**
     * 创建Bean对象
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object createBean(String beanName,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        Object instance = null;
        try {
            instance = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowire.class)) {
                    field.setAccessible(true);
                    field.set(instance,getBean(field.getName()));
                }
            }

            // 如果类实现了BeanNameAware 接口，调用该接口方法传入Bean名称
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 前置处理器调用
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 后置处理器调用
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance,beanName);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return instance;
    }

    /**
     * 根据BeanName获取bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        }

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals("singleton")) {
            Object singletonBean = singletonObject.get(beanName);
            if (singletonBean == null) {
                singletonBean = createBean(beanName,beanDefinition);
                singletonObject.put(beanName,singletonBean);
            }
            return singletonBean;
        }else{
            // 创建原型Bean
            Object prototypeBean = createBean(beanName,beanDefinition);
            return prototypeBean;
        }
    }

    private void scan(Class configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value();
            path = path.replace(".", File.separator);


            // 生成ClassLoader
            ClassLoader classLoader = DuduApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    String absolutePath = f.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace(File.separator,".");

                    try {
                        Class<?> clazz = classLoader.loadClass(absolutePath);
                        if (clazz.isAnnotationPresent(Component.class)) {

                            // 如果BeanPostProcess 是当前类的父类或者父接口
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.getConstructor().newInstance();
                                beanPostProcessorList.add(beanPostProcessor);
                            }

                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if ("".equals(beanName)) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);

                            // 设置Bean的类型
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                String scopeValue = scopeAnnotation.value();
                                beanDefinition.setScope(scopeValue);
                            }else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName,beanDefinition);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }
}