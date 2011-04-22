package ar.com.nny.base.persistence;


@SuppressWarnings("unchecked")
public class LoadByIdsQuery {

	private final String query;

	private final String replaceToken;

	protected LoadByIdsQuery(final String query, final String replaceToken) {
		this.query = query;
		this.replaceToken = replaceToken;
	}


	public LoadByIdsQuery(final LoadQuery lq) {
		this(lq.query(), lq.replaceToken());
	}

	public LoadByIdsQuery(final Class poClass) {
		this("FROM " + poClass.getName() + " WHERE id IN ($IDS$)", "$IDS$");
	}


	public String getQuery() {
		return query;
	}


	public String getReplaceToken() {
		return replaceToken;
	}

}
