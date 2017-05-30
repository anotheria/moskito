package net.anotheria.moskito.extension.nginx;

/**
 * Holder class for values parsed from nginx stub-status module response.
 *
 * @author dzhmud
 */
public final class NginxStatus implements Cloneable {
    private long active;
    private long accepted;
    private long handled;
    private long requests;
    private long reading;
    private long writing;
    private long waiting;

    public long getActive() {
        return active;
    }

    public long getAccepted() {
        return accepted;
    }

    public long getHandled() {
        return handled;
    }

    public long getRequests() {
        return requests;
    }

    public long getReading() {
        return reading;
    }

    public long getWriting() {
        return writing;
    }

    public long getWaiting() {
        return waiting;
    }

    public static Builder newBuilder() {
        return new NginxStatus().new Builder();
    }

    public class Builder {
        public Builder setActive(long active) {
            NginxStatus.this.active = active;
            return this;
        }

        public Builder setAccepted(long accepted) {
            NginxStatus.this.accepted = accepted;
            return this;
        }

        public Builder setHandled(long handled) {
            NginxStatus.this.handled = handled;
            return this;
        }

        public Builder setRequests(long requests) {
            NginxStatus.this.requests = requests;
            return this;
        }

        public Builder setReading(long reading) {
            NginxStatus.this.reading = reading;
            return this;
        }

        public Builder setWriting(long writing) {
            NginxStatus.this.writing = writing;
            return this;
        }

        public Builder setWaiting(long waiting) {
            NginxStatus.this.waiting = waiting;
            return this;
        }

        public NginxStatus build() {
            try {
                return NginxStatus.class.cast(NginxStatus.this.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
