package net.anotheria.moskito.webui.producers.api;

/**
 * Response object for a single value request.
 *
 * @author lrosenberg
 * @since 01.06.18 23:37
 */
public class ValueResponseAO {
	/**
	 * Original request.
	 */
	private ValueRequestPO request;
	/**
	 * Value as string if set (success must be true).
	 */
	private String value;
	/**
	 * If success is false, the retrieval failed, check the message for details.
	 */
	private boolean success;
	/**
	 * Error message if success is false.
	 */
	private String message;

	@Override
	public String toString() {
		return "ValueResponseAO{" +
				"request=" + request +
				", value='" + value + '\'' +
				", success=" + success +
				", message='" + message + '\'' +
				'}';
	}

	public ValueRequestPO getRequest() {
		return request;
	}

	public void setRequest(ValueRequestPO request) {
		this.request = request;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
