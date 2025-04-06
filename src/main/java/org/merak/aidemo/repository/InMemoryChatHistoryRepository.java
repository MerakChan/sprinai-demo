package org.merak.aidemo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InMemoryChatHistoryRepository implements ChatHistoryRepository {

    private Map<String, List<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String type, String chatId) {
        /*if (!chatHistory.containsKey(type)) {
            chatHistory.put(type, new ArrayList<>());
        }
        List<String> chatIds = chatHistory.get(type);*/
        List<String> chatIds = chatHistory.computeIfAbsent(type, k -> new ArrayList<>());
        if (chatIds.contains(chatId)) {
            return;
        }
        chatIds.add(chatId);
    }

    @Override
    public List<String> getChatIds(String type) {
        validateType(type);
        return chatHistory.getOrDefault(type, List.of());
    }

    @Override
    public void delete(String type, String chatId) {
        validateType(type);
        if (chatId == null || chatId.trim().isEmpty()) {
            throw new IllegalArgumentException("chatId不能为空");
        }
        List<String> chatIds = chatHistory.get(type);
        if (chatIds != null) {
            chatIds.remove(chatId);
        }
    }

    @Override
    public void deleteByType(String type) {
        validateType(type);
        chatHistory.remove(type);
    }

    private void validateType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type不能为空");
        }
    }
}