/*******************************************************************************
 * @file   IndustrialNetworkView.java
 *
 * @brief  Lists the nodes and the modules in the project.
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

package org.epsg.openconfigurator.views;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */
public class IndustrialNetworkView extends ViewPart {

    class NameSorter extends ViewerSorter {
    }

    class TreeObject implements IAdaptable {
        private String name;
        private TreeParent parent;

        public TreeObject(String name) {
            this.name = name;
        }

        @Override
        public Object getAdapter(Class key) {
            return null;
        }

        public String getName() {
            return name;
        }

        public TreeParent getParent() {
            return parent;
        }

        public void setParent(TreeParent parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    class TreeParent extends TreeObject {
        private ArrayList children;

        public TreeParent(String name) {
            super(name);
            children = new ArrayList();
        }

        public void addChild(TreeObject child) {
            children.add(child);
            child.setParent(this);
        }

        public TreeObject[] getChildren() {
            return (TreeObject[]) children.toArray(new TreeObject[children
                    .size()]);
        }

        public boolean hasChildren() {
            return children.size() > 0;
        }

        public void removeChild(TreeObject child) {
            children.remove(child);
            child.setParent(null);
        }
    }

    class ViewContentProvider implements IStructuredContentProvider,
            ITreeContentProvider {
        private TreeParent invisibleRoot;

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent) parent).getChildren();
            }
            return new Object[0];
        }

        @Override
        public Object[] getElements(Object parent) {
            if (parent.equals(getViewSite())) {
                if (invisibleRoot == null) {
                    initialize();
                }
                return getChildren(invisibleRoot);
            }
            return getChildren(parent);
        }

        @Override
        public Object getParent(Object child) {
            if (child instanceof TreeObject) {
                return ((TreeObject) child).getParent();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object parent) {
            if (parent instanceof TreeParent) {
                return ((TreeParent) parent).hasChildren();
            }
            return false;
        }

        /*
         * We will set up a dummy model to initialize tree hierarchy. In a real
         * code, you will connect to a real model and expose its hierarchy.
         */
        private void initialize() {
            TreeParent mn = new TreeParent("APC 910 - POWERLINK MN - v2.1.0");
            TreeObject to1 = new TreeObject("X20PS9400");
            TreeObject to2 = new TreeObject("X20DI6391");
            TreeObject to3 = new TreeObject("X20DO6391");
            TreeParent p1 = new TreeParent("X20BC0083");

            p1.addChild(to1);
            p1.addChild(to2);
            p1.addChild(to3);

            TreeObject to4 = new TreeObject("X20PS9400");
            TreeParent p2 = new TreeParent("X20BC0083");
            p2.addChild(to4);

            TreeParent root = new TreeParent("POWERLINK");
            root.addChild(mn);
            mn.addChild(p1);
            mn.addChild(p2);

            invisibleRoot = new TreeParent("");
            invisibleRoot.addChild(root);
        }

        @Override
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }
    }

    class ViewLabelProvider extends LabelProvider {

        @Override
        public Image getImage(Object obj) {
            String imageKey = ISharedImages.IMG_TOOL_FORWARD;
            if (obj instanceof TreeParent) {
                imageKey = ISharedImages.IMG_OBJ_FOLDER;
            }
            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(imageKey);
        }

        @Override
        public String getText(Object obj) {
            return obj.toString();
        }
    }

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "org.epsg.openconfigurator.views.IndustrialNetworkView";

    /*
     * The content provider class is responsible for providing objects to the
     * view. It can wrap existing objects in adapters or simply return objects
     * as-is. These objects may be sensitive to the current input of the view,
     * or ignore it and always show the same content (like Task List, for
     * example).
     */

    private TreeViewer viewer;

    private DrillDownAdapter drillDownAdapter;

    private Action action1;

    private Action action2;

    private Action doubleClickAction;

    /**
     * The constructor.
     */
    public IndustrialNetworkView() {
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        drillDownAdapter = new DrillDownAdapter(viewer);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new ViewLabelProvider());
        viewer.setSorter(new NameSorter());
        viewer.setInput(getViewSite());

        // Create the help context id for the viewer's control
        PlatformUI
                .getWorkbench()
                .getHelpSystem()
                .setHelp(viewer.getControl(),
                        "org.epsg.openconfigurator.viewer");
        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
        contributeToActionBars();
    }

    private void fillContextMenu(IMenuManager manager) {
        manager.add(action1);
        manager.add(action2);
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
        // Other plug-ins can contribute there actions here
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }

    private void fillLocalPullDown(IMenuManager manager) {
        manager.add(action1);
        manager.add(new Separator());
        manager.add(action2);
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(action1);
        manager.add(action2);
        manager.add(new Separator());
        drillDownAdapter.addNavigationActions(manager);
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                IndustrialNetworkView.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                doubleClickAction.run();
            }
        });
    }

    private void makeActions() {
        action1 = new Action() {
            @Override
            public void run() {
                showMessage("Action 1 executed");
            }
        };
        action1.setText("Action 1");
        action1.setToolTipText("Action 1 tooltip");
        action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

        action2 = new Action() {
            @Override
            public void run() {
                showMessage("Action 2 executed");
            }
        };
        action2.setText("Action 2");
        action2.setToolTipText("Action 2 tooltip");
        action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
        doubleClickAction = new Action() {
            @Override
            public void run() {
                ISelection selection = viewer.getSelection();
                Object obj = ((IStructuredSelection) selection)
                        .getFirstElement();
                showMessage("Double-click detected on " + obj.toString());
            }
        };
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    private void showMessage(String message) {
        MessageDialog.openInformation(viewer.getControl().getShell(),
                "Industrial Network", message);
    }
}
