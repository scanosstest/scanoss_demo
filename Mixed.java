/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

public interface InstanceManager {

    Object newInstance(Class<?> clazz) throws IllegalAccessException, InvocationTargetException,
            NamingException, InstantiationException, IllegalArgumentException,
            NoSuchMethodException, SecurityException;

    Object newInstance(String className) throws IllegalAccessException, InvocationTargetException,
            NamingException, InstantiationException, ClassNotFoundException,
            IllegalArgumentException, NoSuchMethodException, SecurityException;

    Object newInstance(String fqcn, ClassLoader classLoader) throws IllegalAccessException,
            InvocationTargetException, NamingException, InstantiationException,
            ClassNotFoundException, IllegalArgumentException, NoSuchMethodException,
            SecurityException;

    void newInstance(Object o)
            throws IllegalAccessException, InvocationTargetException, NamingException;

    void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException;

    /**
     * Called by the component using the InstanceManager periodically to perform
     * any regular maintenance that might be required. By default, this method
     * is a NO-OP.
     */
    default void backgroundProcess() {
        // NO-OP by default
    }
}

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.classic;
import java.io.Serializable;

import org.hibernate.CallbackException;
import org.hibernate.Session;

/**
 * Provides callbacks from the <tt>Session</tt> to the persistent object.
 * Persistent classes <b>may</b> implement this interface but they are not
 * required to.<br>
 * <br>
 * <b>onSave:</b> called just before the object is saved<br>
 * <b>onUpdate:</b> called just before an object is updated,
 * ie. when <tt>Session.update()</tt> is called<br>
 * <b>onDelete:</b> called just before an object is deleted<br>
 * <b>onLoad:</b> called just after an object is loaded<br>
 * <br>
 * <tt>onLoad()</tt> may be used to initialize transient properties of the
 * object from its persistent state. It may <b>not</b> be used to load
 * dependent objects since the <tt>Session</tt> interface may not be
 * invoked from inside this method.<br>
 * <br>
 * A further intended usage of <tt>onLoad()</tt>, <tt>onSave()</tt> and
 * <tt>onUpdate()</tt> is to store a reference to the <tt>Session</tt>
 * for later use.<br>
 * <br>
 * If <tt>onSave()</tt>, <tt>onUpdate()</tt> or <tt>onDelete()</tt> return
 * <tt>VETO</tt>, the operation is silently vetoed. If a
 * <tt>CallbackException</tt> is thrown, the operation is vetoed and the
 * exception is passed back to the application.<br>
 * <br>
 * Note that <tt>onSave()</tt> is called after an identifier is assigned
 * to the object, except when identity column key generation is used.
 *
 * @see CallbackException
 * @author Gavin King
 */
public interface Lifecycle {

	/**
	 * Return value to veto the action (true)
	 */
	public static final boolean VETO = true;

	/**
	 * Return value to accept the action (false)
	 */
	public static final boolean NO_VETO = false;

	/**
	 * Called when an entity is saved.
	 * @param s the session
	 * @return true to veto save
	 * @throws CallbackException Indicates a problem happened during callback
	 */
	public boolean onSave(Session s) throws CallbackException;

	/**
	 * Called when an entity is passed to <tt>Session.update()</tt>.
	 * This method is <em>not</em> called every time the object's
	 * state is persisted during a flush.
	 * @param s the session
	 * @return true to veto update
	 * @throws CallbackException Indicates a problem happened during callback
	 */
	public boolean onUpdate(Session s) throws CallbackException;

	/**
	 * Called when an entity is deleted.
	 * @param s the session
	 * @return true to veto delete
	 * @throws CallbackException Indicates a problem happened during callback
	 */
	public boolean onDelete(Session s) throws CallbackException;

	/**
	 * Called after an entity is loaded. <em>It is illegal to
	 * access the <tt>Session</tt> from inside this method.</em>
	 * However, the object may keep a reference to the session
	 * for later use.
	 *
	 * @param s the session
	 * @param id the identifier
	 */
	public void onLoad(Session s, Serializable id);
}
