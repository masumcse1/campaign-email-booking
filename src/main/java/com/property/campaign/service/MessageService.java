package com.property.campaign.service;

import com.property.campaign.common.dto.MessageFeedback;
import com.property.campaign.common.rdo.MessageRdo;
import com.property.campaign.exception.DataNotFoundException;

import java.util.List;

public interface MessageService {

    public List<MessageRdo> getScheduledMessageData(String token) throws DataNotFoundException;

    List<MessageRdo> getMessagesByPropertyId(Integer propertyId) throws DataNotFoundException;

    public List<MessageFeedback> getMessageFeedbackListByPropertyId(Integer propertyId) throws DataNotFoundException;

    public MessageRdo getMessage(Integer messageId) throws DataNotFoundException;
}
