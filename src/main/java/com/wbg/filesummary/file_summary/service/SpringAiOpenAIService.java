package com.wbg.filesummary.file_summary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpringAiOpenAIService implements OpenAIService {
    private static final Logger logger = LoggerFactory.getLogger(SpringAiOpenAIService.class);

    private final ChatClient chat;

/*    public SpringAiOpenAIService(@Qualifier("openAiChatModel") ChatModel openAiModel) {
        this.chat = ChatClient.create(openAiModel);
    }*/

    public SpringAiOpenAIService(ChatClient.Builder chatBuilder) {
        this.chat = chatBuilder.build();
    }

    @Override
    public String summarize(String text) {
        logger.info("Step 3 -> Summarizing content...");
        return chat.prompt()
                .system("You summarize file concisely.")
                .user("Summarize this:\n" + text)
                .call()
                .content();
    }
}
