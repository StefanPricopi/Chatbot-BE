package nl.fontys.s3.business.impl;

import nl.fontys.s3.persistence.BidJPARepository;
import nl.fontys.s3.persistence.entity.BidEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    private final BidJPARepository bidRepository;

    @Autowired
    public BidService(BidJPARepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public String getBidStatusForUser(int userId) {
        List<BidEntity> bids = bidRepository.findByUserId(userId);
        System.out.println("Bids for user " + userId + ": " + bids);

        if (bids.isEmpty()) {
            return "You have no bids.";
        }

        StringBuilder response = new StringBuilder();
        for (BidEntity bid : bids) {
            response.append("Your bid for the product: \"")
                    .append(bid.getProduct())
                    .append("\" has the status: \"")
                    .append(bid.getBidStatus())
                    .append("\".\n");
        }

        return response.toString();
    }


}
