/*******************************************************************************
 * @file   PowerlinkNetworkProjectNature.java
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

package org.epsg.openconfigurator.builder;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class PowerlinkNetworkProjectNature implements IProjectNature {

  /**
   * ID of this project nature
   */
  public static final String NATURE_ID = "org.epsg.openconfigurator.openConfNature";

  public static void addBuildDescription(IProjectDescription description) {
    ICommand[] commands = description.getBuildSpec();

    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].getBuilderName().equals(PowerlinkNetworkProjectBuilder.BUILDER_ID)) {
        return;
      }
    }

    ICommand[] newCommands = new ICommand[commands.length + 1];
    System.arraycopy(commands, 0, newCommands, 0, commands.length);
    ICommand command = description.newCommand();
    command.setBuilderName(PowerlinkNetworkProjectBuilder.BUILDER_ID);
    newCommands[newCommands.length - 1] = command;
    description.setBuildSpec(newCommands);
  }

  private IProject project;

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.resources.IProjectNature#configure()
   */
  @Override
  public void configure() throws CoreException {
    IProjectDescription desc = project.getDescription();
    ICommand[] commands = desc.getBuildSpec();

    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].getBuilderName().equals(PowerlinkNetworkProjectBuilder.BUILDER_ID)) {
        return;
      }
    }

    ICommand[] newCommands = new ICommand[commands.length + 1];
    System.arraycopy(commands, 0, newCommands, 0, commands.length);
    ICommand command = desc.newCommand();
    command.setBuilderName(PowerlinkNetworkProjectBuilder.BUILDER_ID);
    newCommands[newCommands.length - 1] = command;
    desc.setBuildSpec(newCommands);
    project.setDescription(desc, null);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.resources.IProjectNature#deconfigure()
   */
  @Override
  public void deconfigure() throws CoreException {
    IProjectDescription description = getProject().getDescription();
    ICommand[] commands = description.getBuildSpec();
    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].getBuilderName().equals(PowerlinkNetworkProjectBuilder.BUILDER_ID)) {
        ICommand[] newCommands = new ICommand[commands.length - 1];
        System.arraycopy(commands, 0, newCommands, 0, i);
        System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
        description.setBuildSpec(newCommands);
        project.setDescription(description, null);
        return;
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.resources.IProjectNature#getProject()
   */
  @Override
  public IProject getProject() {
    return project;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core .resources.IProject)
   */
  @Override
  public void setProject(IProject project) {
    this.project = project;
  }

}
