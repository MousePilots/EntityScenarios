package org.mousepilots.es.test.shared;

public interface PersistencyAsync {
	<T,ID> void get(int typeOrdinal, org.mousepilots.es.core.model.HasValue<ID> id, com.google.gwt.user.client.rpc.AsyncCallback<org.mousepilots.es.core.model.HasValue<T>> callback);
	void submit(java.util.List<org.mousepilots.es.core.command.Command> commands, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback);
}