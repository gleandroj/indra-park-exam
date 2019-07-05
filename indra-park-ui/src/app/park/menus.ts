import { MenuItem } from './components';

const prefix = '/indra-park';

export const menus: MenuItem[] = [
    {
        routeLink: `${prefix}/dashboard`,
        icon: 'dashboard',
        title: 'Dashboard'
    },
    {
        routeLink: `${prefix}/operations`,
        icon: 'receipt',
        title: 'Operações'
    }
];