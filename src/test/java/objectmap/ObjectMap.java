
package objectmap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ObjectMap {
	public static String getObjectSearchPath(final String objectName, final String identifier) {
		String searchPath = "";
		try {
			Class<?> clz = Class.forName("pages." + objectName.split("\\.")[0]);
			Object[] consts = clz.getEnumConstants();
			for (int i = 0; i < consts.length; i++) {
				if (consts[i].toString().equalsIgnoreCase(objectName.split("\\.")[1])) {
					Class<?> sub = consts[i].getClass();
					Method mth = sub.getDeclaredMethod("getSearchPath");
					searchPath = (String) mth.invoke(consts[i]);
					break;
				}
			}
			if (!"".equals(identifier)) {
				return getResolvedSearchPath(searchPath, identifier);
			} else {
				return searchPath;
			}
		} catch (Exception e) {
			return "";
		}

	}

	public final List<String> getIdentifiers(final String objectSearchPath) {
		String str;
		List<String> identifires = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(objectSearchPath, "<");
		int idtfIndex = 0;
		while (st.hasMoreElements()) {
			str = st.nextElement().toString();
			if (str.contains(">")) {
				identifires.add(str.split(">")[idtfIndex]);
			}

		}
		return identifires;
	}

	public static List<String> getParameterValues(final String parameters) {
		List<String> parameterValues = new ArrayList<String>();
		String[] st = parameters.split("_PARAM,");
		for (int i = 0; i < st.length; i++) {
			parameterValues.add(st[i]);
		}

		return parameterValues;
	}

	public static String getResolvedSearchPath(final String searchPath, final String identifire) {
		String resolvedSearchPath = searchPath;
		List<String> parameterValues = getParameterValues(identifire);
		for (String param : parameterValues) {
			if (!"".equals(param)) {
				resolvedSearchPath = resolvedSearchPath.replace("<" + param.split("_PARAM:")[0] + ">",
						param.split("_PARAM:")[1]);
			}

		}
		return resolvedSearchPath;
	}
}
