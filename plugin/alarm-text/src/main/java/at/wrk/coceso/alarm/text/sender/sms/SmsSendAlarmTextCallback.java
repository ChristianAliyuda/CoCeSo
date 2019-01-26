package at.wrk.coceso.alarm.text.sender.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class SmsSendAlarmTextCallback implements ListenableFutureCallback<ResponseEntity<String>> {
    private static final Logger LOG = LoggerFactory.getLogger(SmsSendAlarmTextCallback.class);

    @Override
    public void onFailure(final Throwable throwable) {
        LOG.warn("Failed to send alarm text to SMS gateway. Message: {}", throwable.getMessage());
        LOG.debug("Underyling exception", throwable);
    }

    @Override
    public void onSuccess(final ResponseEntity<String> responseBody) {
        LOG.debug("Successfully written alarm text to SMS gateway.");
        LOG.trace("SMS Gateway returned: {}", responseBody);
    }
}
