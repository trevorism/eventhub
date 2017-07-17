package com.trevorism.event.hook

import org.reflections.Reflections

/**
 * @author tbrooks
 */
class HookRegistry {

    static HookRegistry INSTANCE = new HookRegistry()

    private def registry = [:]

    private HookRegistry(){
        new Reflections( 'com.trevorism.event.hook' ).getSubTypesOf( Hook ).each {
            def instance = this.class.classLoader.loadClass(it.name).newInstance()
            registry.put(instance.name, instance)
        }
    }

    Hook getHook(String name){
        return registry[name]
    }

}
