import TaskWarriorClient from '../api/taskWarriorClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create user page of the website.
 */
class CreateUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewUser'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewUser);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
    }

    /**
     * Method to run when the create user submit button is pressed. Call the TaskWarriorService to create the
     * playlist.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const userName = document.getElementById('user-name').value;

        const playlist = await this.client.createUser(userName, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('user', user);
    }

    /**
     * When the user is updated in the datastore, redirect to the view user page.
     */
    redirectToViewUser() {
        const user = this.dataStore.get('user');
        if (user != null) {
            window.location.href = `/users.html?id=${user.id}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createUser = new CreateUser();
    createUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
