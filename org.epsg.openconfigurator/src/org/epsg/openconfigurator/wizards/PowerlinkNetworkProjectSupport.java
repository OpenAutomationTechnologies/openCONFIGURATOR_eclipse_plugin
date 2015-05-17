/*******************************************************************************
 * @file   PowerlinkNetworkProjectSupport.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited
 *                    All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the copyright holders nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package org.epsg.openconfigurator.wizards;

import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.epsg.openconfigurator.builder.PowerlinkNetworkProjectNature;

public class PowerlinkNetworkProjectSupport {
  public static void addNature(IProject project) throws CoreException {
    if (!project.hasNature(PowerlinkNetworkProjectNature.NATURE_ID)) {
      IProjectDescription description = project.getDescription();
      String[] prevNatures = description.getNatureIds();
      String[] newNatures = new String[prevNatures.length + 1];
      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = PowerlinkNetworkProjectNature.NATURE_ID;
      description.setNatureIds(newNatures);

      IProgressMonitor monitor = null;
      project.setDescription(description, monitor);
    }
  }

  public static void addNatureId(IProjectDescription description) {
    String[] prevNatures = description.getNatureIds();
    String[] newNatures = new String[prevNatures.length + 1];
    System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
    newNatures[prevNatures.length] = PowerlinkNetworkProjectNature.NATURE_ID;
    description.setNatureIds(newNatures);
  }

  /**
   * Create a folder structure with a parent root, overlay, and a few child folders.
   *
   * @param newProject
   * @param paths
   * @throws CoreException
   */
  private static void addToProjectStructure(IProject newProject, String[] paths)
      throws CoreException {
    for (String path : paths) {
      IFolder etcFolders = newProject.getFolder(path);
      createFolder(etcFolders);
    }
  }

  /**
   * Just do the basics: create a basic project.
   *
   * @param location
   * @param projectName
   */
  private static IProject createBaseProject(String projectName, URI location) {
    // it is acceptable to use the ResourcesPlugin class
    IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

    if (!newProject.exists()) {
      URI projectLocation = location;
      IProjectDescription desc = newProject.getWorkspace().newProjectDescription(
          newProject.getName());
      if ((location != null)
          && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
        projectLocation = null;
      }

      desc.setLocationURI(projectLocation);
      try {
        newProject.create(desc, null);
        if (!newProject.isOpen()) {
          newProject.open(null);
        }
      } catch (CoreException e) {
        e.printStackTrace();
      }
    }

    return newProject;
  }

  private static void createFolder(IFolder folder) throws CoreException {
    IContainer parent = folder.getParent();
    if (parent instanceof IFolder) {
      createFolder((IFolder) parent);
    }
    if (!folder.exists()) {
      folder.create(false, true, null);
    }
  }

  /**
   * For this marvelous project we need to: - create the default Eclipse project - add the custom
   * project nature - create the folder structure
   *
   * @param projectName
   * @param location
   * @param natureId
   * @return
   */
  public static IProject createProject(String projectName, URI location) {
    Assert.isNotNull(projectName);
    Assert.isTrue(projectName.trim().length() > 0);

    IProject project = createBaseProject(projectName, location);
    try {
      addNature(project);

      String[] paths = { "deviceConfiguration", "deviceImport", "output" }; //$NON-NLS-1$ //$NON-NLS-2$
      addToProjectStructure(project, paths);
    } catch (CoreException e) {
      e.printStackTrace();
      project = null;
    }

    return project;
  }

}
