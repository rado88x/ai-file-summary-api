package com.wbg.filesummary.file_summary.service;

import org.springframework.ai.chat.client.ChatClient;

public class SpringAiOpenAIService implements OpenAIService {
    private final ChatClient chat;

    public SpringAiOpenAIService(ChatClient.Builder chatBuilder) {
        this.chat = chatBuilder.build();
    }

    @Override
    public String summarize(String text) {
        return chat.prompt()
                .system("You summarize file concisely.")
                .user("Summarize this:\n" + text)
                .call()
                .content();
    }
}
