package nl.fontys.s3.business.ChatLogPck;

import nl.fontys.s3.domain.ChatDomains.UpdateChatStatusRequest;

public interface UpdateStatusUC {
    void updateStatus(UpdateChatStatusRequest status);
}
