package com.sa.ecommerce.command;

/**
 * Command Pattern interface
 * Encapsulates order operations as objects
 */
public interface OrderCommand {

    /**
     * Execute the command
     * @return true if command executed successfully, false otherwise
     */
    boolean execute();

    /**
     * Get command description
     * @return Command description
     */
    String getDescription();
}
