package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllChatsResponse {
    @NotNull
    List<ReadChatResponse> allChats;
}
