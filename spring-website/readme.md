
# spring Framework-->Core Technologies  核心技术

## The IoC Container  IOC容器
## Introduction to the Spring IoC Container and Beans
>spring配置元数据方式有三种：
>>XML-based metadata is not the only allowed form of configuration metadata. The Spring IoC container itself is 
>>totally decoupled from the format in which this configuration metadata is actually written. These days, many developers choose Java-based configuration for their Spring applications.
>>1.**XML-based**；2.**Annotation-based configuration**: Spring 2.5 introduced support for annotation-based configuration metadata；
>>3.**Java-based configuration**: Starting with Spring 3.0, many features provided by the Spring JavaConfig project became part of the core Spring Framework. 
>>Thus, you can define beans external to your application classes by using Java rather than XML files. To use these new features, see the @Configuration, @Bean, @Import, and @DependsOn annotations.
>Spring configuration consists of at least one and typically more than one bean definition that the container must manage. XML-based configuration metadata configures these beans as <bean/> elements inside a top-level <beans/> element. Java configuration typically uses @Bean-annotated methods within a @Configuration class.












## Resources 静态资源管理

## Validation, Data Binding, and Type Conversion 数据验证，数据绑定，类型转换

## Spring Expression Language (SpEL)  SpEL表达式

## Aspect Oriented Programming with Spring spring使用AOP编程

## Spring AOP APIs spring AOP的API

## Null-safety null校验

## Data Buffers and Codecs 数据缓冲和校验器