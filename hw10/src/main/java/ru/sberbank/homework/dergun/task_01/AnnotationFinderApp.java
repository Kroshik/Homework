package ru.sberbank.homework.dergun.task_01;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class AnnotationFinderApp {
    private final static String PATH_PROTOTYPE = "Lru/sberbank/homework/common/annotation/Prototype;";
    private final static String PATH_EXPERIMENTAL_FEATURE = "Lru/sberbank/homework/common/annotation/ExperimentalFeature;";
    private final static String PATH = Paths.get("build", "classes", "main", "ru", "sberbank", "homework", "common", "entity").toAbsolutePath().toString();

    public static void main(String[] args) throws IOException {
        final ClassNode cn = new ClassNode();
        final File folder = new File(PATH);
        final File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (final File file : files) {
            System.out.println("file absolute path = " + file.getAbsolutePath());
            final ClassReader cr = new ClassReader(new FileInputStream(file.getAbsolutePath()));
            cr.accept(cn, 0);
            if (cn.invisibleAnnotations == null) {
                continue;
            }
            cn.invisibleAnnotations.stream().filter(node -> Objects.equals(node.desc, PATH_PROTOTYPE)).forEach(node -> {
                System.out.println("Class with annotations @Prototype: " + cn.sourceFile.replace(".java", ""));
                System.out.println("version = " + node.values.get(1));
                System.out.println("Method with annotations @ExperimentalFeature: ");
                for (final MethodNode method : cn.methods) {
                    if (method.invisibleAnnotations == null) {
                        continue;
                    }
                    method.invisibleAnnotations.stream()
                            .filter(annotationNode -> Objects.equals(annotationNode.desc, PATH_EXPERIMENTAL_FEATURE))
                            .forEach(annotationNode -> System.out.println(method.name));
                }
                System.out.println("Field with annotations @ExperimentalFeature: ");
                for (final FieldNode fieldNode : cn.fields) {
                    if (fieldNode.invisibleAnnotations == null) {
                        continue;
                    }
                    fieldNode.invisibleAnnotations.stream()
                            .filter(annotationNode -> Objects.equals(annotationNode.desc, PATH_EXPERIMENTAL_FEATURE))
                            .forEach(annotationNode -> System.out.println(fieldNode.name));
                }
            });
        }
    }
}
