package com.fjq.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * Created by FJQ on 2016/3/4.
 */
public class SharedPreferencesUtil {
    /**
     * 根据保存的值得类型选择不同的保存方式
     *
     * @param context 上下文对象
     * @param name    保存的文件名
     * @param key     键
     * @param object  值
     */
    public static void put(Context context, String name, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sp.edit();
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        }

        SharedPreferencesCompat.apply(mEditor);
    }

    public static Object get(Context context, String name, String key, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }

        return null;
    }

    /**
     * 移除某个键的值
     * @param context
     * @param name
     * @param key
     */
    public static void remove(Context context, String name, String key){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sp.edit();
        mEditor.remove(key);
        SharedPreferencesCompat.apply(mEditor);
    }

    /**
     * 清除所有数据
     * @param context
     * @param name
     */
    public static void clear(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sp.edit();
        mEditor.clear();
        SharedPreferencesCompat.apply(mEditor);
    }

    /**
     * 某个key是否存在
     * @param context
     * @param name
     * @param key
     * @return
     */
    public static boolean contains(Context context, String name, String key){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context,String name)
    {
        SharedPreferences sp = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
