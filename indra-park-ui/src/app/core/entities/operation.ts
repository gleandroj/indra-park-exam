export enum VehicleType {
    Car,
    Motorcycle,
    Truck,
    Pickup
};

export interface Vehicle {
    type: VehicleType;
    model: string;
    plate: string;
};

export enum OperationType {
    IN = 0,
    OUT = 1
};

export interface Operation {
    id?: number;
    createdAt?: string;
    updatedAt?: string;
    vehicle?: Vehicle;
    type?: OperationType;
    enteredAt?: string;
    exitedAt?: string;
}