package com.trevorism.event.hook

import org.reflections.Reflections

/**
 * @author tbrooks
 */
class HookRegistry {

    static final HookRegistry INSTANCE = new HookRegistry()

    private final def registry = [:]

    private HookRegistry(){
        new Reflections( 'com.trevorism.event.hook' ).getSubTypesOf( Hook ).each {
            def instance = this.class.classLoader.loadClass(it.name).newInstance()
            registry.put(instance.name, instance)
        }
    }

    Hook getHook(String name){
        return registry[name]
    }

    Set<String> getAllHooks() {
        registry.keySet()
    }
}
