package transparent.core.database;

import transparent.core.Module;
import transparent.core.ProductID;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * An abstract interface for data persistence.
 * <p/>
 * NOTE: All implementations must be thread-safe.
 */
public interface Database {
    /**
     * Adds the list of module product IDs to the database associated
     * with the given module. If any of the product IDs already exist
     * for that module, then they are not added.
     */
    public boolean addProductIds(Module module, String... moduleProductIds);

    public ResultsIterator<ProductID> getProductIds(Module module);

    @SuppressWarnings("unchecked") /* needed to suppress varargs warning */
    public boolean addProductInfo(Module module,
                                  ProductID moduleProductId,
                                  Entry<String, String>... keyValues);

    public String getMetadata(String key);

    public boolean setMetadata(String key, String value);

    public Results query(ProductID[] rowIds, String[] properties);

    public Results query(String[] whereClause, String[] whereArgs, String sortBy, boolean sortAsc,
                         Integer startRow, Integer numRows, boolean onlyIndexes);

	/* TODO: add API for deleting (both metadata and non-metadata) */

    public void close();

    public interface Results {
        public String getString(int columnIndex);

        public long getLong(int columnIndex);

        public boolean hasNext();

        public boolean next();
    }

    public interface ResultsIterator<T> extends Iterator<T> {
        public boolean seekRelative(int position);
    }
}
