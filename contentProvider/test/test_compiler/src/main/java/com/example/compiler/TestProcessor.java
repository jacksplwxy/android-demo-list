package com.example.compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author feng
 * @desc
 * @time 2018/4/14 10:49
 */
@AutoService(Process.class)
@SupportedAnnotationTypes("com.example.annotation.BindView")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TestProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement typeElement : set) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(typeElement);
            for (Element element : elements) {
                messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName());
            }
        }


        return false;
    }
}
