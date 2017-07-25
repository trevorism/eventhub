package com.trevorism.event.hook
/**
 * @author tbrooks
 */
class HookRegistry {

    static final HookRegistry INSTANCE = new HookRegistry()

    private final def registry = [:]

    private HookRegistry(){
        registerHook(new StoreEventHook())
    }

    void registerHook(Hook hook){
        registry.put(hook.name, hook)
    }

    Hook getHook(String name){
        return registry[name]
    }

    Set<String> getAllHooks() {
        registry.keySet()
    }
}
