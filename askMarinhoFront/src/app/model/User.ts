import { Comment } from "./Comment"
import { Post } from "./Post"
import { Report } from "./Report"
import { Tag } from "./Tag"
import { Upvote } from "./Upvote"

export class User {
    public idUser: number
    public name: string
    public userName: string
    public email: string
    public password: string
    public birth: string
    public gender: string
    public urlImage: string
    public tipo: string
    public comments: Comment[]
    public posts: Post[]
    public reports: Report[]
    public upvotes: Upvote[]
    public favorites: Tag[]
}