package org.merak.aidemo.controller;


import lombok.RequiredArgsConstructor;
import org.merak.aidemo.entity.MessageVO;
import org.merak.aidemo.repository.ChatHistoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/history")
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;

    private final ChatMemory chatMemory;

    /**
     * 查询会话历史列表
     * @param type 业务类型，如：chat,service,pdf
     * @return chatId列表
     */
    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type不能为空");
        }
        return chatHistoryRepository.getChatIds(type);
    }

    /**
     * 根据业务类型、chatId查询会话历史
     * @param type 业务类型，如：chat,service,pdf
     * @param chatId 会话id
     * @return 指定会话的历史消息
     */
    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type不能为空");
        }
        if (chatId == null || chatId.trim().isEmpty()) {
            throw new IllegalArgumentException("chatId不能为空");
        }
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if(messages == null) {
            return List.of();
        }
        return messages.stream().map(MessageVO::new).toList();
    }

    /**
     * 删除指定会话历史
     * @param type 业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    @GetMapping("/delete/{type}/{chatId}")
    public void deleteChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type不能为空");
        }
        if (chatId == null || chatId.trim().isEmpty()) {
            throw new IllegalArgumentException("chatId不能为空");
        }
        chatHistoryRepository.delete(type, chatId);
        chatMemory.clear(chatId);
    }

    /**
     * 删除指定类型的所有会话历史
     * @param type 业务类型，如：chat、service、pdf
     */
    @GetMapping("/delete/{type}")
    public void deleteAllChatHistory(@PathVariable("type") String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type不能为空");
        }
        List<String> chatIds = chatHistoryRepository.getChatIds(type);
        chatIds.forEach(chatMemory::clear);
        chatHistoryRepository.deleteByType(type);
    }
}