package com.viewject.processor;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;


/**
 * AnnotatedClass对象，根据注解元素所对应的类名生成，统一管理一个类下所有的注解元素值
 * 作用：
 * 1、控制动态生成Java文件
 * 2、将注解元素添加到AnnotatedClass对象中统一管理
 * <p>
 * 常用Element子类
 * 1、TypeElement：类
 * 2、ExecutableElement：成员方法
 * 3、VariableElement：成员变量
 * <p>
 * 通过包名和类名获取TypeName，TypeName targetClassName = ClassName.get(“PackageName”, “ClassName”);
 * <p>
 * 通过Element获取TypeName，TypeName type = TypeName.get(element.asType());
 */
public class AnnotatedClass {
    // 注解元素
    private TypeElement typeElement;
    // 元素相关的辅助类
    private Elements mElementUtils;
    // 成员变量集合
    private List<MyBindViewField> mFeild;

    public AnnotatedClass(TypeElement typeElement, Elements mElementUtils) {
        this.typeElement = typeElement;
        this.mElementUtils = mElementUtils;
        mFeild = new ArrayList<>();
    }

    /**
     * 添加一个成员
     */
    public void addField(MyBindViewField field) {
        mFeild.add(field);
    }

    /**
     * 输出Java
     * <p>
     */
    public JavaFile generateFinder() {
        String packageName = getPackageName();
        String className = getClassName();
        ClassName interfaceName = ClassName.get("com.viewinject.bindview", "ViewInjector");
        TypeName classTypeName = TypeName.get(typeElement.asType()); // 类类型

        // 1、构建方法  public void inject(Object object)
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(TypeName.VOID)
                .addParameter(classTypeName, "object");// 入参为Activity


        // 2、为类成员变量赋值  host.textview = (TextView) host.findViewById(R.id.textview)
        for (MyBindViewField field : mFeild) {
            String fieldName = field.getFieldName();
            TypeMirror fieldType = field.getFieldType();
            int resId = field.getResId();
//            methodSpec.addStatement(" if (source !=null && source instanceof View) \n");
            methodSpec.addStatement("if (view !=null && view instanceof View) \n " +
                    "object.$N = view.findViewById($L); \n" +
                    "else \n" +
                    "object.$N = object.findViewById($L)", fieldName, resId, fieldName, resId);
//            methodSpec.addStatement("} else { ", fieldName, ClassName.get(fieldType),  resId);
//            methodSpec.addStatement("object.$N = ($T)object.findViewById($L)", fieldName, ClassName.get(fieldType),  resId);
//            methodSpec.addStatement("}");
        }
        // 3、构建类信息，统一管理注解信息  xxxxViewInjector implements ViewInjector<T>
        TypeSpec typeSpec = TypeSpec.classBuilder(ClassName.get(packageName, className).simpleName() + "ViewInjector")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(interfaceName, classTypeName))
                .addMethod(methodSpec.build())
                .build();
        return JavaFile.builder(packageName, typeSpec).build();
    }

    // 包名
    public String getPackageName() {
        return mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

    // 类名
    public String getClassName() {
        return typeElement.getSimpleName().toString();
    }

    // 获取类的绝对路径
    public String getFullClassName() {
        return typeElement.getQualifiedName().toString();
    }

}
