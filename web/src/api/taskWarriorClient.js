import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the TaskWarriorService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class TaskWarriorClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getUser', 'getStore', 'createUser', 'getUserTasks', 
            'getEncounter', 'createEncounter', 'removeTaskFromList', 'attackMonster', 'spellMonster', 'markComplete', 'updateUserName',
        'createNewTask', 'useItem', 'buyItem'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }
    /**
    * Gets the user for the given id
    */
    async getUser(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get users.");
            const response = await this.axiosClient.get(`users`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
        /**
    * updates the name of the user
    */
    async updateUserName(name, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get users.");
            const response = await this.axiosClient.put(`users`, {
                name: name
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

        /**
* updates the name of the user
*/
    async createNewTask(taskName, taskType, difficulty, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get users.");
            const response = await this.axiosClient.put(`users/task`, {
                "taskType": taskType,
                "task": {
                    "taskType": taskType,
                    "taskName": taskName,
                    "difficulty": difficulty,
                    "completed": false,
                }
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
        /**
* Gets the user for the given id
*/
async getEncounter(errorCallback) {
    try {
        const token = await this.getTokenOrThrow("Only authenticated users can get encounters.");
        const response = await this.axiosClient.get(`encounter`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.encounter;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}

            /**
* Gets the user for the given id
*/
async createEncounter(errorCallback) {
    try {
        const token = await this.getTokenOrThrow("Only authenticated users can get encounters.");
        const response = await this.axiosClient.post(`encounter`, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.encounter;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}
        /**
* Gets the user tasks for the given id and type
*/
async getUserTasks(taskType, errorCallback) {
    try {
        console.log("Recieved task type of: %s", taskType);
        const token = await this.getTokenOrThrow("Only authenticated users can create users.");
        const response = await this.axiosClient.get(`users/task`, {
            headers: {
                Authorization: `Bearer ${token}`
            },
            body: {
            taskType: taskType
            }
        });
        return response.data.taskList;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}
/**
    * Gets the store for the given assets
    */
    async getStore(errorCallback) {
        try {
            const response = await this.axiosClient.get('assets', {
                });
            return response.data.assets;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
     * Create a new user owned by the current user.
     * @param name The name of the user to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user that has been created.
     */
    async createUser(name, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create users.");
            const response = await this.axiosClient.post(`users`, {
                name: name,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


                /**
     * marks a task as completed and adds gold.
     */
    async markComplete(taskName, taskType, difficulty, errorCallback) {

        try {
            const token = await this.getTokenOrThrow("Only authenticated users can remove a task from a tasklist.");
            const response = await this.axiosClient.put(`users/task/complete`, {
                "task": {
                    "taskType": taskType,
                    "taskName": taskName,
                    "difficulty": difficulty,
                    "completed": false,
                }
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    
                    /**
 * marks a task as completed and adds gold.
 */
async updateTask(taskName, taskType, difficulty, newTaskName, newTaskType, newDifficulty, errorCallback) {
    let nullSafeType;
    if (newTaskType.includes("null")) {
        nullSafeType = taskType;
    } else {
        nullSafeType = newTaskType;
    }
    let nullSafeDifficulty;
    if (newDifficulty.includes("null")) {
        nullSafeDifficulty = difficulty;
    } else {
        nullSafeDifficulty = newDifficulty;
    }
    let nullSafeName;
    if (newTaskName === "") {
        nullSafeName = taskName;
    } else {
        nullSafeName = newTaskName;
    }

    try {
        const token = await this.getTokenOrThrow("Only authenticated users can remove a task from a tasklist.");
        const response = await this.axiosClient.put(`users/task/update`, {
            "task": {
                "taskType": taskType,
                "taskName": taskName,
                "difficulty": difficulty,
                "completed": false,
            },
            "newDifficulty": nullSafeDifficulty,
            "newType": nullSafeType,
            "newName": nullSafeName
        }, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.userModel;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
    }

    async startNewDay(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can start days.");
            const response = await this.axiosClient.put(`newday`, {
                
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

        /**
     * removes a task from a list.
     * @param task The task in the list to remove
     * @returns the updated user.
     */
    async removeTaskFromList(taskName, taskType, difficulty, errorCallback) {

        try {
            const token = await this.getTokenOrThrow("Only authenticated users can remove a task from a tasklist.");
            const response = await this.axiosClient.delete(`/users/task`, { data: { "task": {
                    "taskName": taskName,
                    "taskType": taskType,
                    "difficulty": difficulty,
                    "completed": false }},
                    headers: { Authorization: `Bearer ${token}` } });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async useItem(assetType, assetId, assetDescription, assetName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can use an item.");
            const response = await this.axiosClient.delete(`/users/inventory`, { data: { "asset": {
                "assetId": assetId,
                "assetType": assetType,
                "description": assetDescription,
                "name": assetName }},
                headers: { Authorization: `Bearer ${token}` } });
        return response.data.user;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
    }

    async buyItem(assetType, assetId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can buy an item.");
            const response = await this.axiosClient.put(`users/inventory`, {
                "assetType": assetType,
                "assetId": assetId,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
        return response.data.user;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
    }

    async attackMonster(monsterTarget, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can attack monsters.");
            const response = await this.axiosClient.put(`/attack`, {
                attackPower: 20,
                staminaNeeded: 10,
                target: monsterTarget,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async spellMonster(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can attack monsters.");
            const response = await this.axiosClient.put(`/spell`, {
                "spell": {
                    "attackPower": 15,
                    "manaNeeded": 30
                    }
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async monsterTurn(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can be attacked.");
            const response = await this.axiosClient.put(`/monsterturn`, 
            {
                
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
