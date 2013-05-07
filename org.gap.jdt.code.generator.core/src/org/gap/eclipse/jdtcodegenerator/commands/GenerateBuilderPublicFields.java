package org.gap.eclipse.jdtcodegenerator.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

public class GenerateBuilderPublicFields extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        MessageDialog.openInformation(null, "Action executed", "Executed");
        return null;
    }

}
