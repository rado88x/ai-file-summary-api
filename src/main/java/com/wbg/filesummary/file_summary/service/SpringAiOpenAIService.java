package com.wbg.filesummary.file_summary.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpringAiOpenAIService implements OpenAIService {
    private final ChatClient chat;

/*    public SpringAiOpenAIService(@Qualifier("openAiChatModel") ChatModel openAiModel) {
        this.chat = ChatClient.create(openAiModel);
    }*/

    public SpringAiOpenAIService(ChatClient.Builder chatBuilder) {
        this.chat = chatBuilder.build(); // resolves to the active provider
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
