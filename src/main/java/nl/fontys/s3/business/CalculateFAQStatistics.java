package nl.fontys.s3.business;

import nl.fontys.s3.domain.FAQStatistics;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CalculateFAQStatistics {

    public List<FAQStatistics> calculateMostAskedFAQs(int topN);
    public int calculateOutOfOfficeChats();
    public List<String> getFailedQuestions();
}
