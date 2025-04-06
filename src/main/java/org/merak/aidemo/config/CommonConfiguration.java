package org.merak.aidemo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    // 参数中的model为使用的模型
    @Bean
    public ChatClient chatClient(OllamaChatModel model, ChatMemory chatMemory) {
        return ChatClient.builder(model) // 创建ChatClient工厂
                .defaultSystem("你是一名java程序员，善于回答java技术相关问题。")
                .defaultAdvisors(new SimpleLoggerAdvisor()) // 添加日志Advisor
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)) // 添加会话记忆Advisor
                .build(); // 构建ChatClient实例

    }

    //添加会话记忆Advisor
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}

