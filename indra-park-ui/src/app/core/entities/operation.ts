export enum VehicleType {
    Car,
    Motorcycle,
    Truck,
    Pickup
};

export const vehicleTypesLabels = [
    {
        type: VehicleType.Car,
        text: "Carro",
    },
    {
        type: VehicleType.Motorcycle,
        text: "Moto",
    },
    {
        type: VehicleType.Pickup,
        text: "Caminhonete",
    },
    {
        type: VehicleType.Truck,
        text: "Caminhão",
    }
];

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