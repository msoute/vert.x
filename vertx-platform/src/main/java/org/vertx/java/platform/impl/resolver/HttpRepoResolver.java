/*
 * Copyright (c) 2011-2013 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package org.vertx.java.platform.impl.resolver;/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import org.vertx.java.core.Vertx;
import org.vertx.java.platform.impl.ModuleIdentifier;

import java.net.URI;

public abstract class HttpRepoResolver implements RepoResolver {

  protected final Vertx vertx;
  protected final String repoScheme;
  protected final String repoHost;
  protected final String repoUsername;
  protected final String repoPassword;
  protected final int repoPort;
  protected final String contentRoot;

  public HttpRepoResolver(Vertx vertx, String repoID) {
    this.vertx = vertx;
    try {
      URI uri = new URI(repoID);
      if (uri.getUserInfo() != null && uri.getUserInfo().contains(":")) {
        repoUsername = uri.getUserInfo().substring(0, uri.getUserInfo().indexOf(":"));
        repoPassword = uri.getUserInfo().substring(uri.getUserInfo().indexOf(":")+1);
      } else {
        repoUsername = null;
        repoPassword = null;
      }

      repoScheme = uri.getScheme();
      repoHost = uri.getHost();
      int port = uri.getPort();
      if (port == -1 ) {
        port = (repoScheme.equals("https")) ? 443 : 80;
      }
      repoPort = port;
      contentRoot = uri.getPath();
    } catch (Exception e) {
      throw new IllegalArgumentException(repoID + " is not a valid repository identifier");
    }
  }

  public abstract boolean getModule(String filename, ModuleIdentifier moduleIdentifier);

}
