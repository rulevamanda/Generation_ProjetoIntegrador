import { Comment } from "./Comment"
import { Post } from "./Post"
import { Tag } from "./Tag"


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
    public description: string
    public comments: Comment[]
    public posts: Post[]
    public favorites: Tag[]
    public upvotePost: Post[]
    public upvoteComment: Comment[]
    public reportPost: Post[]
    public reportComment: Comment[]
}