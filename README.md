# AI 聊天演示应用

这是一个基于Spring Boot和Spring AI框架开发的AI聊天演示应用，集成了Ollama大语言模型，提供了聊天功能和聊天历史管理功能。

## 项目介绍

本项目是一个轻量级的AI聊天应用后端服务，使用Spring AI与Ollama模型进行集成，支持多会话管理和历史记录查询。项目采用内存存储方式保存聊天历史，适合学习和演示Spring AI的使用方法。

## 技术栈

- **Spring Boot**: 应用框架
- **Spring AI**: AI集成框架
- **Ollama**: 本地大语言模型服务
- **Lombok**: 简化Java代码
- **Reactor**: 响应式编程支持

## 功能特性

- 基于Ollama的AI聊天功能
- 多会话管理（支持不同业务类型的聊天）
- 聊天历史记录存储与查询
- 响应式流式输出聊天结果

## 项目结构

```
src/main/java/org/merak/aidemo/
├── AiDemoApplication.java        # 应用程序入口
├── config/                       # 配置类
│   ├── CommonConfiguration.java  # 通用配置
│   └── MvcConfiguration.java     # MVC配置
├── controller/                   # 控制器
│   ├── ChatController.java       # 聊天控制器
│   └── ChatHistoryController.java # 聊天历史控制器
├── entity/                       # 实体类
│   └── MessageVO.java            # 消息实体
└── repository/                   # 数据访问层
    ├── ChatHistoryRepository.java # 聊天历史仓库接口
    └── InMemoryChatHistoryRepository.java # 内存实现的聊天历史仓库
```

## 安装与配置

### 前置条件

- JDK 17+
- Maven 3.6+
- Ollama 服务（本地或远程）

### 安装步骤

1. 克隆项目到本地

```bash
git clone <repository-url>
cd ai-demo
```

2. 安装依赖

```bash
mvn clean install
```

3. 配置Ollama

确保Ollama服务已启动，并在`application.yaml`中配置正确的URL和模型：

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434  # Ollama服务地址
      chat:
        model: deepseek-r1:7b           # 使用的模型
        options:
          temperature: 0.8              # 温度参数
```

4. 启动应用

```bash
mvn spring-boot:run
```

## API接口

### 聊天接口

- **URL**: `/ai/chat`
- **方法**: GET
- **参数**:
  - `prompt`: 聊天提示词（默认值："讲个笑话"）
  - `chatId`: 会话ID（必填）
- **返回**: 流式文本响应

### 聊天历史接口

#### 获取会话ID列表

- **URL**: `/ai/history/{type}`
- **方法**: GET
- **参数**:
  - `type`: 业务类型（如：chat, service, pdf）
- **返回**: 会话ID列表

#### 获取特定会话的历史记录

- **URL**: `/ai/history/{type}/{chatId}`
- **方法**: GET
- **参数**:
  - `type`: 业务类型
  - `chatId`: 会话ID
- **返回**: 会话历史消息列表

## 使用示例

### 发起聊天

```bash
curl "http://localhost:8080/ai/chat?prompt=你好，请介绍一下自己&chatId=session123"
```

### 获取聊天历史

```bash
curl "http://localhost:8080/ai/history/chat"
curl "http://localhost:8080/ai/history/chat/session123"
```

## 开发指南

### 添加新的业务类型

在现有代码基础上，您可以通过以下步骤添加新的业务类型：

1. 在调用聊天API时，使用新的业务类型作为`type`参数
2. 系统会自动为新业务类型创建会话记录

### 自定义模型配置

您可以在`application.yaml`中修改Ollama模型配置，包括使用的模型和参数设置。

## 许可证

[MIT License](LICENSE)