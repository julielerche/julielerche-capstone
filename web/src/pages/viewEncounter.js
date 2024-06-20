import BindingClass from "../util/bindingClass";
/**
 * Logic needed for the view playlist page of the website.
 */
export default class ViewEncounter extends BindingClass {
    constructor(client, dataStore) {
        super();
        this.client = client;
        this.dataStore = dataStore;
        this.bindClassMethods(['clientLoaded', 'mount', 'spellMonster', 'addEncounterToPage', 
            'createNewEncounterSubmit', 'addMonsterTargetsToPage', 'attackMonster', 'monsterTurn'], this);
        this.dataStore.addChangeListener(this.addEncounterToPage);
        this.dataStore.addChangeListener(this.addMonsterTargetsToPage);
        
        console.log("viewEncounter constructor");
    }

    /**
     * Once the client is loaded, get the encounter.
     */
    async clientLoaded() {
        const encounter = await this.client.getEncounter();
        this.dataStore.set('encounter', encounter);
        this.dataStore.set('monsterList', encounter.monsterList);
        const user = this.dataStore.get("user");
        this.dataStore.set('stamina', user.stamina);
        this.dataStore.set('mana', user.mana);
        this.dataStore.set('health', user.health);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        document.getElementById('createEncounter').addEventListener('click', this.createNewEncounterSubmit);
        document.getElementById('swordAttack').addEventListener('click', this.attackMonster);
        document.getElementById('spellAttack').addEventListener('click', this.spellMonster);
        this.clientLoaded();
    }

    addMonsterTargetsToPage(){
        const monsterList = this.dataStore.get('monsterList');
        if (monsterList == null) {
            return;
        }
        let optionHTML = `<div class="form-group">
                            <label for="exampleFormControlSelect1">Select Monster</label>
                            <select class="form-control" id="exampleFormControlSelect1">`;
        let monster;
        let counter = 1;
        for (monster of monsterList) {
            optionHTML += `<option value="${counter}">${monster.name}</option>`;
            counter++;
        }
        optionHTML += `</select>
                         </div>`;
        document.getElementById('monsterOptions').innerHTML = optionHTML;
    }

    /**
* When encounterr is updated in the datastore, updates user metadata on page
*/
    addEncounterToPage() {
        const monsterList = this.dataStore.get('monsterList');
        if (monsterList == null || monsterList.length == 0) {
            const noEncounterHTML = `<div class="card" style="width: 18rem;">
            <p>Click the new Encounter button below to generate a new encounter.</p>
            </div>`
            document.getElementById('encounter-monsters').innerHTML = noEncounterHTML;
            return;
        }
        const numberOfMonsters = monsterList.length;
        let encounterHTML;
        if (numberOfMonsters === 3) {
            encounterHTML = '<div class="row row-cols-1 row-cols-md-3 g-4">';
        } else if (numberOfMonsters === 2) {
            encounterHTML = '<div class="row row-cols-1 row-cols-md-2 g-1">';
        } else {
            encounterHTML = '<div class="row row-cols-1 row-cols-md-1 g-4">';
        }
        
        let monster;
        for (monster of monsterList) {
            var healthPercent = (monster.currentHealth / monster.startingHealth) * 100;
            encounterHTML += `
            <div class="col">
                <div class="card border-danger" style="width: 12rem;">
                <img class="card-img-top" src="sprites/${monster.name}.png" alt="Card image cap">
                    <h5 class="card-title">${monster.name}</h5>`;

                    if (monster.description == "easy") {
                        encounterHTML += `<span class="description">
                        ★☆☆</span>`;
                    } else if (monster.description == "medium") {
                        encounterHTML += `<span class="description">
                        ★★☆</span>`;
                    } else {
                        encounterHTML += `<span class="description">
                        ★★★</span>`;
                    }
                    encounterHTML += `
                    <span class="attackPower">Attack Power: ${monster.attackPower}</span>
                    <div class="progress">
                        <div class="progress-bar bg-danger" role="progressbar" style="width: ${healthPercent}%" aria-valuenow="${monster.currentHealth}" aria-valuemin="0" aria-valuemax="${monster.startingHealth}"></div>
                    </div>
                    </div>
                
                
            `;
            encounterHTML += `</div>`
        }
        document.getElementById('encounter-monsters').innerHTML = encounterHTML;
    }



     /**
* When user is updated in the datastore, updates user metadata on page
*/
async createNewEncounterSubmit(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message-encounter');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');
    const monsterList = this.dataStore.get("monsterList");

    //prevents new encounter if currently fighting
    if (monsterList.length !== 0) {
        errorMessageDisplay.innerText = `You are already in an encounter!`;
        errorMessageDisplay.classList.remove('hidden');
        return;
    }

    const createButton = document.getElementById('createEncounter');
    const origButtonText = createButton.innerText;
    createButton.innerText = 'Loading...';

    const encounter = await this.client.createEncounter((error) => {
        createButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('monsterList', encounter.monsterList);
    createButton.innerText = origButtonText;
}

     /**
* Uses an attack to update the encounter.
*/
async attackMonster(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message-monsters');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');
    
    const stamina = this.dataStore.get("stamina");
    const userGold = this.dataStore.get("gold");

    const attackButton = document.getElementById('swordAttack');
    const monsterTarget = document.getElementById('exampleFormControlSelect1').value;
    const origButtonText = attackButton.innerText;
    attackButton.innerText = 'Loading...';

    const response = await this.client.attackMonster(monsterTarget, (error) => {
        attackButton.innerText = origButtonText;
        if (error.message === "null" && stamina < 10) {
            errorMessageDisplay.innerText = `Error: Not enough stamina!`;
        } else {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
        }
        errorMessageDisplay.classList.remove('hidden');
        return;
    });
    this.dataStore.set('monsterList', response.data.assets);
    if (response.data.goldEarned !== 0) {
        this.dataStore.set("gold", response.data.goldEarned + userGold);
        const alertHTML =`<div class="alert alert-success fade show">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Success!</strong> You earned ${response.data.goldEarned} gold.
        </div>`
        document.getElementById('monsterKilledAlert').innerHTML = alertHTML;
    }
    attackButton.innerText = origButtonText;
    const newStamina = stamina - 10;
    document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('aria-valuenow',newStamina);
    document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('style','width:'+Number(newStamina)+'%');
    if (response.data.assets.length > 0) {
        this.monsterTurn();
    }
    
}
     /**
* Uses an attack to update the encounter.
*/
async spellMonster(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message-monsters');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');
    const mana = this.dataStore.get("mana");

    const spellButton = document.getElementById('spellAttack');
    const origButtonText = spellButton.innerText;
    spellButton.innerText = 'Loading...';

    const response = await this.client.spellMonster((error) => {
        spellButton.innerText = origButtonText;
        if (error.message === "null" && mana < 30) {
            errorMessageDisplay.innerText = `Error: Not enough mana!`;
        } else {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
        }
        errorMessageDisplay.classList.remove('hidden');
        return;
    });
    this.dataStore.set('monsterList', response.data.assets);
    if (response.data.goldEarned !== 0) {
        this.dataStore.set("gold", response.data.goldEarned + user.gold);
        const alertHTML =`<div class="alert alert-success fade show">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Success!</strong> You earned ${response.data.goldEarned} gold.
        </div>`
        document.getElementById('monsterKilledAlert').innerHTML = alertHTML;
    }
    const newMana = mana - 30;
    document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('aria-valuenow',newMana);
    document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('style','width:'+Number(newMana)+'%');
    //this.dataStore.set('user', user);
    
    spellButton.innerText = origButtonText;
    this.monsterTurn();

}

async monsterTurn() {
    const monsterList = this.dataStore.get("monsterList");
    if (monsterList === null) {
        return;
    }
    const user = await this.client.monsterTurn((error) => {
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        return;
    });    
    let attackPower = 0;
    let monster;
    for (monster of monsterList) {
        attackPower += monster.attackPower;
    }
    const alertHTML =`<div class="alert alert-danger fade show">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Monsters Attack!</strong> Lost ${attackPower} points of health.
        </div>`
        document.getElementById('monsterKilledAlert').innerHTML = alertHTML;
    this.dataStore.set("user", user);

}

}

//     /**
//  * Main method to run when the page contents have loaded.
//  */
// const main = async () => {
//     const viewEncounter = new ViewEncounter();
//     viewEncounter.mount();
// };

//window.addEventListener('DOMContentLoaded', main);
