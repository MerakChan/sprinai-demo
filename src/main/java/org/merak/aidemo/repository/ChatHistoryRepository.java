package org.merak.aidemo.repository;

import java.util.List;

public interface ChatHistoryRepository {

    /**
     * 保存会话记录
     * @param type 业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    void save(String type, String chatId);

    /**
     * 获取会话ID列表
     * @param type 业务类型，如：chat、service、pdf
     * @return 会话ID列表
     */
    List<String> getChatIds(String type);

    /**
     * 删除指定会话历史
     * @param type 业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    void delete(String type, String chatId);

    /**
     * 删除指定类型的所有会话历史
     * @param type 业务类型，如：chat、service、pdf
     */
    void deleteByType(String type);
}