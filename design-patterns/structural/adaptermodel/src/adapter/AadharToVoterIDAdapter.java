package adapter;

import model.Aadhar;
import model.VoterID;
import util.LoggerUtil;

import java.util.UUID;

public class AadharToVoterIDAdapter {

    public static VoterID convert(Aadhar aadhar) throws Exception {
        if(aadhar == null) {
            throw new IllegalArgumentException("Aadhar cannot be null");
        }

        if(aadhar.getPerson().getAge() < 18) {
            LoggerUtil.log("Person is under 18. Cannot generate VoterID.");
            return null;
        }

        // Generate a random VoterID
        String voterID = "VOTER-" + UUID.randomUUID().toString().substring(0, 8);
        LoggerUtil.log("VoterID generated: " + voterID);
        return new VoterID(voterID, aadhar.getPerson());
    }
}
