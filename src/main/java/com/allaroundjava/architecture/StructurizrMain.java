package com.allaroundjava.architecture;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class StructurizrMain {
    private static final String HTTP_SOAP = "HTTP Soap";
    private static final String HTTP_REST = "HTTP REST";
    private static final String EXTERNAL = "external";
    private static final String DATABASE = "Database";
    private static final String QUEUE = "Queue";
    private static final String SPRING_DATA_JPA = "Spring Data JPA";
    private static final String ACTIVE_MQ = "Active MQ";
    private static final String SPRING_BOOT = "Spring Boot";
    private static final String H_2 = "H2";

    public static void main(String[] args) throws StructurizrClientException {
        Workspace workspace = new Workspace("Cards App", "Model Describing Credit Card Application Functionality");
        Model model = workspace.getModel();

        Person customer = model.addPerson("Bank Customer", "Customer using the credit card");
        SoftwareSystem creditCards = model.addSoftwareSystem("Credit Cards", "Handles Credit Card Operations");
        customer.uses(creditCards, "Use and pay back their credit cards, View Reports, Manage cards");

        ViewSet views = workspace.getViews();
        SystemContextView contextView = views.createSystemContextView(creditCards, "Credit Cards System Context", "Credit Cards system high Level View");
        ContainerView containerView = buildContainerView(creditCards, views, customer);
        buildStyles(views);
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        StructurizrClient structurizrClient = new StructurizrClient("cdd2fbb3-8253-44bb-a528-922c7a1eafc4", "49dba874-b8bc-449a-995d-6fb116e5b710");
        structurizrClient.putWorkspace(55291, workspace);
    }

    private static void buildStyles(ViewSet views) {
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(EXTERNAL).background("#cfcfcf");
        styles.addElementStyle(DATABASE).shape(Shape.Cylinder);
        styles.addElementStyle(QUEUE).shape(Shape.Pipe);
    }

    private static ContainerView buildContainerView(SoftwareSystem creditCards, ViewSet views, Person customer) {
        ContainerView containerView = views.createContainerView(creditCards, "Credit Cards Containers", "Credit Cards systems containers");

        Container cardOperations = creditCards.addContainer("Card Operations", "Handling Credit Card Operations", SPRING_BOOT);
        Container cardOpsDatabase = creditCards.addContainer("Card Operations DB", "Card Operations DB", H_2);
        cardOpsDatabase.addTags(DATABASE);
        cardOperations.uses(cardOpsDatabase, "Store and Retrieve Card Operations Data", SPRING_DATA_JPA);

        Container statements = creditCards.addContainer("Statements", "Generation of statements", SPRING_BOOT);
        Container statementsDb = creditCards.addContainer("Statements DB", "Statements DB", H_2);
        statementsDb.addTags(DATABASE);
        statements.uses(statementsDb, "Store and retrieve card operations for statements", SPRING_DATA_JPA);
        Container plasticCards = creditCards.addContainer("Plastic Cards", "Handling Plastic Cards", SPRING_BOOT);
        Container plasticCardsDb = creditCards.addContainer("Plastic Cards DB", "Plastic cards DB", H_2);
        plasticCardsDb.addTags(DATABASE);
        plasticCards.uses(plasticCardsDb, "Store and retrieve plastic card data", SPRING_DATA_JPA);

        Container queues = creditCards.addContainer("Message Broker", "Pass on Messages", ACTIVE_MQ);
        queues.addTags(QUEUE);
        cardOperations.uses(queues, "Notifies of Operations", ACTIVE_MQ);
        queues.uses(statements, "Informs of statement related operation happening", ACTIVE_MQ);

        buildComponentsView(new Containers(cardOperations, cardOpsDatabase, queues, plasticCards, plasticCardsDb), views, customer);

        containerView.addAllElements();
        return containerView;
    }

    private static void buildComponentsView(Containers containers, ViewSet views, Person customer) {
        ComponentView operationsComponentView = views.createComponentView(containers.getCardOperations(), "Components in Card Operations", "Components in Card Operations");
        ComponentView plasticCardComponentsView = views.createComponentView(containers.getPlasticCards(), "Components in Plastic Cards", "Components in Plastic Cards");
        Component withdrawalsController = containers.getCardOperations().addComponent("Withdrawals Controller", "Lists and accepts withdrawals", HTTP_REST);
        Component creditCardsController = containers.getCardOperations().addComponent("Credit Cards Controller", "Handles credit card repayments", HTTP_REST);
        Component personalizationController = containers.getPlasticCards().addComponent("Personalizations Controller", "Enables configuration of Plastic Credit Cards", HTTP_REST);
        Component ordersController = containers.getPlasticCards().addComponent("Orders Controller", "Enables ordering Plastic Credit Cards", HTTP_REST);

        Component withdrawing = containers.getCardOperations().addComponent("Withdrawing", "Handler withdrawing operations");
        Component repaying = containers.getCardOperations().addComponent("Repaying", "Handler repayments");
        Component personalizations = containers.getPlasticCards().addComponent("Personalization", "Handles plastic card personalizations");
        Component orders = containers.getPlasticCards().addComponent("Ordering", "Handles Plastic cards Orders");

        withdrawalsController.uses(withdrawing, "Performs and lists withdrawals");
        creditCardsController.uses(repaying, "Repays a credit card specific amounts");
        personalizationController.uses(personalizations, "Creates/Updates plastic card info", HTTP_REST);
        ordersController.uses(orders, "Requests and views plastic card orders", HTTP_REST);

        withdrawing.uses(containers.getQueues(), "Informs of statement event");
        withdrawing .uses(containers.getCardOpsDatabase(), "Stores withdrawal information");
        repaying.uses(containers.getCardOpsDatabase(), "Stores Repayment information");
        repaying.uses(containers.getQueues(), "Informs of repaid card");
        personalizations.uses(containers.getPlasticCardsDb(), "Stores personalization information");
        orders.uses(containers.getPlasticCardsDb(), "Stores Order information");

        customer.uses(withdrawalsController, "Withdraws money", HTTP_REST);
        customer.uses(creditCardsController, "Repays Money", HTTP_REST);
        customer.uses(personalizationController, "Handles plastic cards", HTTP_REST);
        customer.uses(ordersController, "Orders plastic cards", HTTP_REST);

        operationsComponentView.addAllElements();
        plasticCardComponentsView.addAllElements();

    }

    private static class Containers {
        private final Container cardOperations;
        private final Container cardOpsDatabase;
        private final Container queues;
        private Container plasticCards;
        private Container plasticCardsDb;

        private Containers(Container cardOperations, Container cardOpsDatabase, Container queues, Container plasticCards, Container plasticCardsDb) {
            this.cardOperations = cardOperations;
            this.cardOpsDatabase = cardOpsDatabase;
            this.queues = queues;
            this.plasticCards = plasticCards;
            this.plasticCardsDb = plasticCardsDb;
        }

        public Container getCardOperations() {
            return cardOperations;
        }

        public Container getCardOpsDatabase() {
            return cardOpsDatabase;
        }

        public Container getQueues() {
            return queues;
        }

        public Container getPlasticCards() {
            return plasticCards;
        }

        public Container getPlasticCardsDb() {
            return plasticCardsDb;
        }
    }
}
