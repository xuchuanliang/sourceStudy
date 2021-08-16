package com.test1.ant.myJdkProxy.v1;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 代理工具类
 * 手动模拟jdk动态代理
 * 步骤：根据传进来的目标代理对象，动态实现代理逻辑的代理java对象文件
 * .java文件-->编译成class文件-->加载至jvm虚拟机内存，称为class对象-->反射创建代理对象-->调用代理方法实现代理
 *
 */
public class ProxyUtil {

    /**
     * 生成java文件，假设生成在D盘下，包名是com.ant
     * @param target 需要被代理的目标对象
     * @param interfc 目标对象实现的接口
     * @return
     */
    public static Object getProxy(Object target,Class interfc) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(null == target || null == interfc){
            throw new IllegalArgumentException("参数不允许为空");
        }
        if(!interfc.isInterface()){
            throw new IllegalArgumentException("必须是接口");
        }
        //1.生成java源代码字符串
        StringBuilder sb = genJavaStr(target,interfc);
        //2.将源代码写成$Proxy.java至D:/com/ant下
        File file = createJavaFile(sb.toString());
        //3.将$Proxy.java动态编译成字节码
        compile(file);
        //4.将class文件动态加载到jvm虚拟机内存中
        Class clazz = loadClass();
        //5.反射创建代理对象
        Constructor<?> constructor = clazz.getConstructor(target.getClass());
        return constructor.newInstance(target);
    }

    /**
     * 生成源代码字符串
     * package com.test1.ant
     * public class $Proxy implement interface{
     *     private Object target;
     *
     *     public $Proxy (Object target){
     *         this.target = target;
     *     }
     *
     *     //重写各个interface中的方法，并且在前后加上逻辑，执行目标对象的方法
     *      @Override
     *      public returnType methosName(args){
     *          System.out.println("before")
     *          returntype result = target.methosName(args);
                System.out.println("after");
     *          return result;
     *      }
     * }
     */

    private static StringBuilder genJavaStr(Object target,Class interfc) {
        String line = "\r\n";
        String tab = "\t";
        StringBuilder sb = new StringBuilder();
        sb
                .append("package com.test1.ant;")
                .append(line)
                .append("public class $Proxy implements ").append(interfc.getName()).append(" {")
                .append(line)
                .append(tab)
                .append("private ").append(target.getClass().getName()).append(" target;").append(line)
                .append(tab).append("public $Proxy(").append(target.getClass().getName()).append(" target").append("){").append(line)
                .append(tab).append(tab).append("this.target = target;").append(line)
                .append(tab).append("}")
                .append(line);
        //方法
        Method[] methods = interfc.getMethods();
        if(null!=methods && methods.length>0){
            for(Method m:methods){
                sb.append(line).append(tab)
                        .append("@Override").append(line)
                        .append(tab)
                        //方法名称
                        .append("public ").append(m.getReturnType().getName()).append(" ").append(m.getName()).append("(");
                Class<?>[] parameterTypes = m.getParameterTypes();
                //增加方法参数
                String modalityParam = "";
                if(null!=parameterTypes && parameterTypes.length>0){
                    StringBuilder sbu = new StringBuilder();
                    //var1,var2,var3  目标对象方法调用时使用
                    StringBuilder modalitySbu = new StringBuilder();
                    for(int i=0;i<parameterTypes.length;i++){
                        sbu.append(" ").append(parameterTypes[i].getName()).append(" var"+i).append(",");
                        modalitySbu.append("var"+i).append(",");
                    }
                    String temp = sbu.toString();
                    sb.append(temp.substring(0,sbu.lastIndexOf(",")));
                    String tempPa = modalitySbu.toString();
                    modalityParam = tempPa.substring(0,tempPa.lastIndexOf(","));
                }
                sb.append("){").append(line)
                        .append(tab).append(tab)
                        .append("System.out.println(\"方法逻辑处理之前\")").append(";").append(line)
                        .append(tab).append(tab);
                        if(void.class!=m.getReturnType()){
                            sb.append("return ");
                        }
                sb.append("target.").append(m.getName()).append("(").append(modalityParam).append(");").append(line)
                        .append(tab).append("}");
            }
        }
        sb.append(line).append("}");
        return sb;
    }

    /**
     * 根据源代码，创建java文件
     * @param sourceCode
     * @return
     * @throws IOException
     */
    private static File createJavaFile(String sourceCode) throws IOException {
        File dir = new File("D:\\com\\ant");
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File("D:\\com\\ant\\$Proxy.java");
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(sourceCode);
        fileWriter.flush();
        fileWriter.close();
        return file;
    }

    /**
     * 将源代码编译成字节码文件
     * @param file
     * @throws IOException
     */
    private static void compile(File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
        Iterable units = fileMgr.getJavaFileObjects(file);
        JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
        t.call();
        fileMgr.close();
    }

    /**
     * 动态加载class文件到jvm内存中
     * @return
     */
    private static Class loadClass() throws MalformedURLException, ClassNotFoundException {
        URL[] urls = new URL[]{new URL("file:D:\\\\")};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class<?> clazz = urlClassLoader.loadClass("com.test1.ant.$Proxy");
        return clazz;
    }
}
