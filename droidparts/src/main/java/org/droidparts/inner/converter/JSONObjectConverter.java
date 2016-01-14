/**
 * Copyright 2013 Alex Yanchenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.droidparts.inner.converter;

import org.droidparts.inner.TypeHelper;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

public class JSONObjectConverter extends Converter<JSONObject> {

	@Override
	public boolean canHandle(Class<?> cls) {
		return TypeHelper.isJSONObject(cls);
	}

	@Override
	public String getDBColumnType() {
		return TEXT;
	}

	@Override
	public <V> void putToJSON(Class<JSONObject> valType,
			Class<V> componentType, JSONObject obj, String key, JSONObject val)
			throws JSONException {
		obj.put(key, val.toString());
	}

	@Override
	public <V> JSONObject readFromJSON(Class<JSONObject> valType,
			Class<V> componentType, JSONObject obj, String key)
			throws JSONException {
		return parseFromString(valType, componentType, obj.getString(key));
	}

	@Override
	protected <V> JSONObject parseFromString(Class<JSONObject> valType,
			Class<V> componentType, String str) {
		try {
			return new JSONObject(str);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public <V> void putToContentValues(Class<JSONObject> valueType,
			Class<V> componentType, ContentValues cv, String key, JSONObject val) {
		cv.put(key, val.toString());
	}

	@Override
	public <V> JSONObject readFromCursor(Class<JSONObject> valType,
			Class<V> componentType, Cursor cursor, int columnIndex) {
		try {
			return new JSONObject(cursor.getString(columnIndex));
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
