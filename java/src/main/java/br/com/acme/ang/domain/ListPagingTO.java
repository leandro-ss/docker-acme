package br.com.acme.ang.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * ListPagingTO
 */
public class ListPagingTO {

    public static final Integer PAGING_SIZE = 10;

    private String nextContinuationToken;

    private List<String> listFileName = new ArrayList<>(PAGING_SIZE);

	/**
	 * @return the listFileName
	 */
	public List<String> getListFileName() {
		return listFileName;
	}

	/**
	 * @return the nextContinuationToken
	 */
	public String getNextContinuationToken() {
		return nextContinuationToken;
	}

	/**
	 * @param nextContinuationToken the nextContinuationToken to set
	 */
	public void setNextContinuationToken(String nextContinuationToken) {
		this.nextContinuationToken = nextContinuationToken;
	}

	/**
	 * @param listFileName the listFileName to set
	 */
	public void setListFileName(List<String> listFileName) {
		this.listFileName = listFileName;
	}
    
    @Override
    public boolean equals(Object obj) {
        Boolean result = Boolean.FALSE;

        if (obj instanceof ListPagingTO){
            result = ((ListPagingTO)obj).getListFileName().equals(this.listFileName);
        }

        return result;
    }

    @Override
    public String toString() {
        return this.listFileName != null ? this.listFileName.toString(): null;
    }
}