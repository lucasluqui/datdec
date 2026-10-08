package com.lucasluqui.datdec.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil
{
  /**
   * Finds a public instance method by signature rather than by name, for use with obfuscated
   * classes whose method names may change between releases. Throws if there isn't exactly one match.
   */
  public static Method findMethod (Class<?> clazz, Class<?> returnType, Class<?>... paramTypes)
  {
    List<Method> matches = new ArrayList<>();
    for (Method method : clazz.getMethods()) {
      if (Modifier.isStatic(method.getModifiers()) || method.isSynthetic() || method.isBridge()) continue;
      if (method.getDeclaringClass() == Object.class) continue;
      if (method.getReturnType() != returnType) continue;
      if (!Arrays.equals(method.getParameterTypes(), paramTypes)) continue;
      matches.add(method);
    }

    if (matches.size() != 1) {
      throw new IllegalStateException("Expected exactly one method in " + clazz.getName()
        + " returning " + returnType.getName() + " with parameters " + Arrays.toString(paramTypes)
        + ", found " + matches.size() + ": " + matches);
    }
    return matches.get(0);
  }
}
