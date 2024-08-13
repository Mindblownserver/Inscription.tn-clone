
interface Student{
    cin: string;
    nomFrEtu: string;
    prenomFrEtu: string;
    nomArEtu: string;
    prenomArEtu: string;
    dateNaiss: Date;
    nomPere: string;
    prenomPere: string;
    professionPere: string;
    nomMere: string;
    prenomMere: string;
    professionMere: string;
    photoEtu: Uint8Array;
}

interface Inscription {
    etu: Student;
    au: string;
    fill_etab: FilliereEtablissement;
    paid: number; 
}

interface FilliereEtablissement{
    etab:Etablissement,
    fill:Filliere,
    periode: number,
    prixTotal: number,
    prixTr1: number,
    prixTr2: number
}

interface Filliere{
    idFill: string,
    nomArFill: string,
    nomFrFill: string,
    niveau:number
}

interface Etablissement{
    idEtab: string,
    nomFrEtab: string,
    nomArEtab: string,
    uni: University,
    photoEtab:Uint8Array
}

interface University{
    idUni: string,
    nomFrUni: string,
    nomArUni: string,
    photoUni: Uint8Array
}

export {Student, Inscription, FilliereEtablissement, University, Etablissement, Filliere};
 