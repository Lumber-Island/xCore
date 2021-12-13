package xyz.dwaslashe.lang.cache;

import xyz.dwaslashe.lang.Lang;

import java.util.HashMap;
import java.util.Map;

public class LangCache {

    private final Map<String, Lang> langMap = new HashMap<>();

    public Map<String, Lang> getLangMap() {
        return langMap;
    }
}
