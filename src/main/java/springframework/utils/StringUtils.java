package springframework.utils;

import cn.hutool.core.lang.Assert;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author gusixue
 * @description 框架内 String 的工具类
 * @date 2023/4/1
 */
public abstract class StringUtils {

    private static final String FOLDER_SEPARATOR = "/";

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";

    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * Check whether the given object (possibly a {@code String}) is empty.
     * This is effectively a shortcut for {@code !hasLength(String)}.
     * <p>This method accepts any Object as an argument, comparing it to
     * {@code null} and the empty String. As a consequence, this method
     * will never return {@code true} for a non-null non-String object.
     * <p>The Object signature is useful for general attribute handling code
     * that commonly deals with Strings but generally has to iterate over
     * Objects since attributes may e.g. be primitive value objects as well.
     * <p><b>Note: If the object is typed to {@code String} upfront, prefer
     * {@link #hasLength(String)} or {@link #hasText(String)} instead.</b>
     * @param str the candidate object (possibly a {@code String})
     * @since 3.2.1
     * @see #hasLength(String)
     * @see #hasText(String)
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of length 0.
     * <p>Note: this method returns {@code true} for a {@code String} that
     * purely consists of whitespace.
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has length
     * @see #hasLength(CharSequence)
     * @see #hasText(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     * @see #hasLength(String)
     * @see Character#isWhitespace
     */
    public static boolean hasText( String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replace all occurrences of a substring within a string with another string.
     * @param inString {@code String} to examine
     * @param oldPattern {@code String} to replace
     * @param newPattern {@code String} to insert
     * @return a {@code String} with the replacements
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);

        int pos = 0;  // our position in the old string
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString, pos, index);
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        // append any characters to the right of a match
        sb.append(inString, pos, inString.length());
        return sb.toString();
    }

    /**
     * Delete any character in a given {@code String}.
     * @param inString the original {@code String}
     * @param charsToDelete a set of characters to delete.
     * E.g. "az\n" will delete 'a's, 'z's and new lines.
     * @return the resulting {@code String}
     */
    public static String deleteAny(String inString,  String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }

        StringBuilder sb = new StringBuilder(inString.length());
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Normalize the path by suppressing sequences like "path/.." and
     * inner simple dots.
     * <p>The result is convenient for path comparison. For other uses,
     * notice that Windows separators ("\") are replaced by simple slashes.
     * <p><strong>NOTE</strong> that {@code cleanPath} should not be depended
     * upon in a security context. Other mechanisms should be used to prevent
     * path-traversal issues.
     * @param path the original path
     * @return the normalized path
     */
    public static String cleanPath(String path) {
        if (!hasLength(path)) {
            return path;
        }
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

        // Strip prefix from path to analyze, to not treat it as part of the
        // first path element. This is necessary to correctly parse paths like
        // "file:core/../core/io/Resource.class", where the ".." should just
        // strip the first "core" directory while keeping the "file:" prefix.
        int prefixIndex = pathToUse.indexOf(':');
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.contains(FOLDER_SEPARATOR)) {
                prefix = "";
            }
            else {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
        }
        if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
            prefix = prefix + FOLDER_SEPARATOR;
            pathToUse = pathToUse.substring(1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
        LinkedList<String> pathElements = new LinkedList<>();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            String element = pathArray[i];
            if (CURRENT_PATH.equals(element)) {
                // Points to current directory - drop it.
            }
            else if (TOP_PATH.equals(element)) {
                // Registering top path found.
                tops++;
            }
            else {
                if (tops > 0) {
                    // Merging path element with element corresponding to top path.
                    tops--;
                }
                else {
                    // Normal path element found.
                    pathElements.addFirst(element);
                }
            }
        }

        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++) {
            pathElements.addFirst(TOP_PATH);
        }
        // If nothing else left, at least explicitly point to current path.
        if (pathElements.size() == 1 && pathElements.getLast().isEmpty() && !prefix.endsWith(FOLDER_SEPARATOR)) {
            pathElements.addFirst(CURRENT_PATH);
        }

        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * Determine the RFC 3066 compliant language tag,
     * as used for the HTTP "Accept-Language" header.
     * @param locale the Locale to transform to a language tag
     * @return the RFC 3066 compliant language tag as {@code String}
     * @deprecated as of 5.0.4, in favor of {@link Locale#toLanguageTag()}
     */
    @Deprecated
    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
    }

    /**
     * Copy the given {@link Collection} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     * @param collection the {@code Collection} to copy
     * (potentially {@code null} or empty)
     * @return the resulting {@code String} array
     */
    public static String[] toStringArray( Collection<String> collection) {
        return (collection != null ? collection.toArray(new String[0]) : new String[0]);
    }

    /**
     * Merge the given {@code String} arrays into one, with overlapping
     * array elements only included once.
     * <p>The order of elements in the original arrays is preserved
     * (with the exception of overlapping elements, which are only
     * included on their first occurrence).
     * @param array1 the first array (can be {@code null})
     * @param array2 the second array (can be {@code null})
     * @return the new array ({@code null} if both given arrays were {@code null})
     * @deprecated as of 4.3.15, in favor of manual merging via {@link LinkedHashSet}
     * (with every entry included at most once, even entries within the first array)
     */
    @Deprecated

    public static String[] mergeStringArrays( String[] array1,  String[] array2) {
        if (isEmpty(array1)) {
            return array2;
        }
        if (isEmpty(array2)) {
            return array1;
        }

        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(array1));
        for (String str : array2) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        return toStringArray(result);
    }

    /**
     * Take a {@code String} that is a delimited list and convert it into a
     * {@code String} array.
     * <p>A single {@code delimiter} may consist of more than one character,
     * but it will still be considered as a single delimiter string, rather
     * than as bunch of potential delimiter characters, in contrast to
     * {@link #tokenizeToStringArray}.
     * @param str the input {@code String} (potentially {@code null} or empty)
     * @param delimiter the delimiter between elements (this is a single delimiter,
     * rather than a bunch individual delimiter characters)
     * @return an array of the tokens in the list
     * @see #tokenizeToStringArray
     */
    public static String[] delimitedListToStringArray( String str,  String delimiter) {
        return delimitedListToStringArray(str, delimiter, null);
    }

    /**
     * Take a {@code String} that is a delimited list and convert it into
     * a {@code String} array.
     * <p>A single {@code delimiter} may consist of more than one character,
     * but it will still be considered as a single delimiter string, rather
     * than as bunch of potential delimiter characters, in contrast to
     * {@link #tokenizeToStringArray}.
     * @param str the input {@code String} (potentially {@code null} or empty)
     * @param delimiter the delimiter between elements (this is a single delimiter,
     * rather than a bunch individual delimiter characters)
     * @param charsToDelete a set of characters to delete; useful for deleting unwanted
     * line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a {@code String}
     * @return an array of the tokens in the list
     * @see #tokenizeToStringArray
     */
    public static String[] delimitedListToStringArray(
            String str,  String delimiter,  String charsToDelete) {

        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[] {str};
        }

        List<String> result = new ArrayList<>();
        if (delimiter.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
            }
        }
        else {
            int pos = 0;
            int delPos;
            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                pos = delPos + delimiter.length();
            }
            if (str.length() > 0 && pos <= str.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(deleteAny(str.substring(pos), charsToDelete));
            }
        }
        return toStringArray(result);
    }

    /**
     * Convert a {@link Collection} to a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     * @param coll the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim the delimiter to use (typically a ",")
     * @param prefix the {@code String} to start each element with
     * @param suffix the {@code String} to end each element with
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString(
            Collection<?> coll, String delim, String prefix, String suffix) {

        if (isEmpty(coll)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     * @param coll the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString( Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Determine whether the given array is empty:
     * i.e. {@code null} or of zero length.
     * @param array the array to check
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

}