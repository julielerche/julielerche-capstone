import TaskWarriorClient from '../api/taskWarriorClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class ViewUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage', 'addTasksToPage', 'addInventoryToPage', 'addStatsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addUserToPage);
        //this.dataStore.addChangeListener(this.addStoreToPage);
        this.dataStore.addChangeListener(this.addTasksToPage);
        this.dataStore.addChangeListener(this.addInventoryToPage);
        this.dataStore.addChangeListener(this.addStatsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        document.getElementById('user-name').innerText = "loading user ...";
        const user = await this.client.getUser();
       // const dailies = await this.client.getUserTasks("DAILY");
        this.dataStore.set('user', user);
        //this.dataStore.set('dailies', dailies);
        //this.dataStore.set('store', store);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
        this.clientLoaded();
    }

    /**
    * When user is updated in the datastore, updates user metadata on page
    */
    addUserToPage() {
        const user = this.dataStore.get('user');
        if (user == null) {
            return;
         }
        document.getElementById('user-name').innerText = user.displayName;
    }

    /**
     * adds user inventory to the page tab.
     */
    addInventoryToPage() {
        const user = this.dataStore.get('user');
        const inventory = user.inventory;
        if (inventory == null) {
            return;
        }
        
        let inventoryHTML = '<div class="row">';
        let assetCounter = 0;
        let asset;
        for (asset of inventory) {
            assetCounter++;
            inventoryHTML += `
                <div class="card" style="width: 12rem;">
                    <img class="card-img-top" src="sprites/${asset.name}.png" alt="Card image cap">
                    <div class="card-body">
                    <h5 class="card-title">${asset.name}</h5>
                    <p class="card-text">${asset.description}</p>
                    <a href="#" class="btn btn-primary">Use Item</a>
                    </div>
                    </div>
            `;
            if (assetCounter % 2 == 0) {
                inventoryHTML += `</div>
                <div class="row">
                `;
            }
        }
        inventoryHTML += `</div>`;
        document.getElementById('user-inventory').innerHTML = inventoryHTML;

    }

        /**
     * adds user stats to the progress bars.
     */
        addStatsToPage() {
            const user = this.dataStore.get('user');
            const health = user.health;
            if (health == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-danger').item(0).setAttribute('aria-valuenow',health);
            document.getElementsByClassName('progress-bar bg-danger').item(0).setAttribute('style','width:'+Number(health)+'%');
    
            const mana = user.mana;
            if (mana == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('aria-valuenow',mana);
            document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('style','width:'+Number(mana)+'%');
    
            const stamina = user.stamina;
            if (stamina == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('aria-valuenow',stamina);
            document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('style','width:'+Number(stamina)+'%');
    
        }

    /**
     * Gets user tasks in each tab
     */
    addTasksToPage() {
        const user = this.dataStore.get('user');
        const dailies = user.dailies;
        if (dailies == null) {
            return;
        }

        let taskHTML = '';
        let task;
        for (task of dailies) {
            taskHTML += `
            <div class="card">
                <div class="container">
  <div class="row">
    <div class="col">
    ${task.taskName}
    </div>
    <div class="col col-lg-2">
      ★☆☆
    </div>
    <div class="col col-lg-2">
      ✔
    </div>
  </div>
</div></div>
            `;
        }
        document.getElementById('daily-tasks').innerHTML = taskHTML;

        const chores = user.chores;
        if (chores == null) {
            return;
        }

        let choreHTML = '';
        let chore;
        choreHTML += `<div class="panel-group">
                     <div class="panel panel-default">`;
        for (chore of chores) {
            choreHTML += `
                       <div class="panel-heading">
                           <h4 class="panel-title">
                             <a data-toggle="collapse" href="#collapse1">${task.taskName}</a>
                           </h4>
                       </div>
            `;
        }
        choreHTML += `<div id="collapse1" class="panel-collapse collapse">
                    <div class="panel-body">Panel Body</div>
                   <div class="panel-footer">Panel Footer</div>
                    </div>
              </div>
            </div>`;
        document.getElementById('chore-tasks').innerHTML = choreHTML;

        const todos = user.toDos;
        if (todos == null) {
            return;
        }

        let todoHTML = '';
        let todo;
        for (todo of todos) {
            todoHTML += `<p>
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
            
                <div class="card-body">
                    <h5>${todo.taskName}
                    `;
            if (todo.difficulty == "EASY") {
                todoHTML += `
                ★☆☆`;
            }
            if (todo.completed) {
                todoHTML += `
                ✔`;
            }
            todoHTML += ` </h5>
                    
                    </div>
                </a>
                </p>
                <div class="collapse" id="collapseExample">
                    <div class="card card-body">
                        Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident.
                    </div>
             </div>
            `;
        }
        document.getElementById('todo-tasks').innerHTML = todoHTML;
    }
/**
    * When store button is pushed, adds store to page
    */
    // addStoreToPage() {
    //     const store = this.dataStore.get('store');
    //     if (store == null) {
    //         return;
    //      }
    //     let storeHTML = '';
    //     let asset;
    //     for (asset of store.assets) {
    //         storeHTML += '<div class="tag">' + asset + '</div>';
    //     }
    //     document.getElementById('store').innerHTML = storeHTML;
    // }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewUser = new ViewUser();
    viewUser.mount();
};

window.addEventListener('DOMContentLoaded', main);