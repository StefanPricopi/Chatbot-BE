package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllChatsResponse {
    List<ReadChatResponse> allChats;
}
